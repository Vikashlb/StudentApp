terraform {
  required_providers {
    aws = {
        source = "hashicorp/aws"
        version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "student" {
  ami           = "ami-076c6dbba59aa92e6"
  instance_type = "t2.micro"
  tags = {
    Name = "student"
  }
}