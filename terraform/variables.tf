variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "ap-southeast-1"
}

variable "app_name" {
  description = "Application name"
  type        = string
  default     = "ecommerce-backend"
}

variable "ecr_repository_url" {
  description = "ECR repository URL"
  type        = string
}

variable "jwt_secret" {
  description = "JWT secret key"
  type        = string
  sensitive   = true
}