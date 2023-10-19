resource "aws_iam_role" "ecs_task_execution_role" {
  name = "${var.name}_ecs_task_execution_role"

  assume_role_policy = <<EOF
    {
    "Version": "2012-10-17",
    "Statement": [
       {
         "Action": "sts:AssumeRole",
         "Principal": {
           "Service": "ecs-tasks.amazonaws.com"
         },
         "Effect": "Allow",
         "Sid": ""
       }
    ]
    }
    EOF
}

resource "aws_iam_policy" "ecs_task_execution_policy" {
  name        = "mixfast_ecs_execution_policy"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Action = [
        "ecr:GetAuthorizationToken",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "ecr:BatchCheckLayerAvailability",
        "ecr:PutImage",
        "ecr:InitiateLayerUpload",
        "ecr:UploadLayerPart",
        "ecr:CompleteLayerUpload",
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents",
        "logs:DescribeLogStreams"
      ]
      Resource = ["*"]
    }]
  })
}

resource "aws_iam_role" "ecs_task_role" {
  name = "${var.name}_ecs_task_role"

  assume_role_policy = <<EOF
    {
     "Version": "2012-10-17",
     "Statement": [
       {
         "Action": "sts:AssumeRole",
         "Principal": {
           "Service": "ecs-tasks.amazonaws.com"
         },
         "Effect": "Allow",
         "Sid": ""
       }
     ]
    }
    EOF
}

resource "aws_iam_policy" "ecs_task_policy" {
  name        = "mixfast_ecs_service_policy"
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [{
      Effect = "Allow"
      Action = [
        "elasticloadbalancing:Describe*",
        "elasticloadbalancing:DeregisterInstancesFromLoadBalancer",
        "elasticloadbalancing:RegisterInstancesWithLoadBalancer",
        "ec2:Describe*",
        "ec2:AuthorizeSecurityGroupIngress",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "ecr:BatchCheckLayerAvailability",
        "ecr:PutImage",
        "ecr:InitiateLayerUpload",
        "ecr:UploadLayerPart",
        "ecr:CompleteLayerUpload"
      ]
      Resource = ["*"]
    }]
  })
}

resource "aws_iam_role_policy_attachment" "ecs_task_attachment" {
  policy_arn = aws_iam_policy.ecs_task_policy.arn
  role = aws_iam_role.ecs_task_role.name
}

resource "aws_iam_role_policy_attachment" "ecs_task_execution_attachment" {
  policy_arn = aws_iam_policy.ecs_task_execution_policy.arn
  role = aws_iam_role.ecs_task_execution_role.name
}