-- =====================================================
-- Add UNIQUE constraint on user_id for vet table
-- =====================================================
-- This ensures each user can only have one vet profile

-- First, remove any duplicate vet records (keep the first one for each user)
DELETE v1 FROM vet v1
INNER JOIN vet v2 
WHERE v1.id > v2.id 
  AND v1.user_id = v2.user_id 
  AND v1.user_id IS NOT NULL;

-- Add unique constraint if not exists
ALTER TABLE vet 
ADD CONSTRAINT uk_vet_user_id UNIQUE (user_id);



