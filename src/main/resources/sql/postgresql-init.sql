-- 首先确保 vector 扩展已安装
CREATE EXTENSION IF NOT EXISTS vector;

-- 创建向量存储表
CREATE TABLE IF NOT EXISTS public.vector_store (
    id varchar(255) PRIMARY KEY,
    content text,
    metadata jsonb,
    embedding vector(1536)
    );

-- 创建向量索引（可选，但建议创建以提高查询性能）
CREATE INDEX ON public.vector_store
    USING hnsw (embedding vector_cosine_ops);