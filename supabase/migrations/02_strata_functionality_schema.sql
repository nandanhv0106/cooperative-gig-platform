-- Migration 02: Saved Addresses, Saved Workers, and Customer Preferences

-- 1. Extend profiles table with phone number if missing
ALTER TABLE profiles ADD COLUMN IF NOT EXISTS phone TEXT;

-- 2. Customer Saved Addresses Table
CREATE TABLE IF NOT EXISTS customer_addresses (
    id BIGSERIAL PRIMARY KEY,
    customer_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
    label VARCHAR(50) NOT NULL DEFAULT 'Home', -- Home, Work, Other
    address TEXT NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    is_default BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

ALTER TABLE customer_addresses ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Customers can manage own addresses" ON customer_addresses
    FOR ALL USING (auth.uid() = customer_id);

-- 3. Customer Saved Workers Table
CREATE TABLE IF NOT EXISTS saved_workers (
    id BIGSERIAL PRIMARY KEY,
    customer_id UUID NOT NULL REFERENCES profiles(id) ON DELETE CASCADE,
    worker_id UUID NOT NULL REFERENCES workers(id) ON DELETE CASCADE,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(customer_id, worker_id)
);

ALTER TABLE saved_workers ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Customers can manage own saved workers" ON saved_workers
    FOR ALL USING (auth.uid() = customer_id);

-- Indexes for quick lookups
CREATE INDEX IF NOT EXISTS idx_customer_addresses ON customer_addresses(customer_id);
CREATE INDEX IF NOT EXISTS idx_saved_workers ON saved_workers(customer_id);
