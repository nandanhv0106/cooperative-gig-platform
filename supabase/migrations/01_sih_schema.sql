-- Cooperative Gig Platform SIH Database Migration Script

-- 1. Profiles Table RLS & Role Enforcement
CREATE TABLE IF NOT EXISTS profiles (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER' CHECK (role IN ('CUSTOMER', 'WORKER', 'ADMIN')),
    first_name TEXT,
    last_name TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

ALTER TABLE profiles ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can read own profile" ON profiles
    FOR SELECT USING (auth.uid() = id);

CREATE POLICY "Users can insert own profile" ON profiles
    FOR INSERT WITH CHECK (auth.uid() = id);

CREATE POLICY "Users can update own profile" ON profiles
    FOR UPDATE USING (auth.uid() = id);

-- 2. Workers Table
CREATE TABLE IF NOT EXISTS workers (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    cooperative_id BIGINT,
    skills TEXT[],
    verification_status VARCHAR(20) DEFAULT 'PENDING' CHECK (verification_status IN ('PENDING', 'VERIFIED', 'REJECTED')),
    is_available BOOLEAN DEFAULT TRUE,
    rating NUMERIC(3,2) DEFAULT 5.0,
    total_earnings NUMERIC(10,2) DEFAULT 0.0,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

ALTER TABLE workers ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Anyone can view verified workers" ON workers
    FOR SELECT USING (verification_status = 'VERIFIED' OR auth.uid() = id);

CREATE POLICY "Workers can update own status" ON workers
    FOR UPDATE USING (auth.uid() = id);

-- 3. Services Table
CREATE TABLE IF NOT EXISTS services (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    base_price NUMERIC(10,2) DEFAULT 0.0,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

ALTER TABLE services ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Public read services" ON services
    FOR SELECT USING (true);

-- 4. Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    id BIGSERIAL PRIMARY KEY,
    customer_id UUID NOT NULL REFERENCES profiles(id),
    worker_id UUID REFERENCES workers(id),
    service_id BIGINT NOT NULL REFERENCES services(id),
    status VARCHAR(30) DEFAULT 'SEARCHING' CHECK (status IN ('SEARCHING', 'ASSIGNED', 'ACCEPTED', 'ON_THE_WAY', 'STARTED', 'COMPLETED', 'CANCELLED')),
    location TEXT NOT NULL,
    description TEXT,
    is_emergency BOOLEAN DEFAULT FALSE,
    total_amount NUMERIC(10,2) DEFAULT 0.0,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

ALTER TABLE bookings ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Customers can view own bookings" ON bookings
    FOR SELECT USING (auth.uid() = customer_id OR auth.uid() = worker_id);

CREATE POLICY "Customers can create bookings" ON bookings
    FOR INSERT WITH CHECK (auth.uid() = customer_id);

CREATE POLICY "Participants can update booking status" ON bookings
    FOR UPDATE USING (auth.uid() = customer_id OR auth.uid() = worker_id);

-- 5. Worker Documents Table
CREATE TABLE IF NOT EXISTS worker_documents (
    id BIGSERIAL PRIMARY KEY,
    worker_id UUID NOT NULL REFERENCES workers(id) ON DELETE CASCADE,
    document_type TEXT NOT NULL,
    file_url TEXT NOT NULL,
    status TEXT DEFAULT 'PENDING',
    created_at TIMESTAMPTZ DEFAULT NOW()
);

ALTER TABLE worker_documents ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Workers can view own documents" ON worker_documents
    FOR SELECT USING (auth.uid() = worker_id);

CREATE POLICY "Workers can upload own documents" ON worker_documents
    FOR INSERT WITH CHECK (auth.uid() = worker_id);

-- Indexes for fast query performance
CREATE INDEX IF NOT EXISTS idx_workers_available ON workers(is_available, verification_status);
CREATE INDEX IF NOT EXISTS idx_bookings_customer ON bookings(customer_id);
CREATE INDEX IF NOT EXISTS idx_bookings_status ON bookings(status);
