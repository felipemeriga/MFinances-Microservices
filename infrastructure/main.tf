resource "aws_dynamodb_table" "basic-dynamodb-table-plannings" {
  name           = "plannings"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "id"
  range_key      = "category"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "category"
    type = "S"
  }

  tags = {
    Name        = "dynamodb-table-plannings"
    Environment = "dev"
  }
}

resource "aws_dynamodb_table" "basic-dynamodb-table-expenses" {
  name           = "expenses"
  billing_mode   = "PROVISIONED"
  read_capacity  = 20
  write_capacity = 20
  hash_key       = "id"
  range_key      = "category"

  attribute {
    name = "id"
    type = "S"
  }

  attribute {
    name = "category"
    type = "S"
  }

  tags = {
    Name        = "dynamodb-table-expenses"
    Environment = "dev"
  }
}

resource "aws_s3_bucket" "s3_public_bucket" {
  bucket = "MFinance-Frontend"
  region = "us-east-1"
  acl    = "public-read"
  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }

  website {
    index_document = "index.html"
    error_document = "error.html"
  }

  # Setting versioning as defined by user
  versioning {
    enabled = true
  }

  policy = <<POLICY
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::MFinance-Frontend/*"
        }
    ]
}
POLICY
}

resource "aws_s3_bucket_public_access_block" "public_access" {
  bucket = aws_s3_bucket.s3_public_bucket

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false

}
