resource "aws_ecs_task_definition" "mixfast_ecs_task_definition" {
  family                   = "family-${var.name}"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = var.cpu
  memory                   = var.memory
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn
  task_role_arn            = aws_iam_role.ecs_task_role.arn
  tags                     = var.tags

  runtime_platform {
    operating_system_family = "LINUX"
    cpu_architecture        = "X86_64"
  }

  container_definitions    = <<DEFINITION
  [
    {
      "name": "container-${var.name}",
      "image": "022874923015.dkr.ecr.us-east-2.amazonaws.com/mixfast:latest",
      "essential": true,
      "portMappings": [
        {
          "containerPort": ${var.port},
          "hostPort": ${var.port}
        }
      ],
      "memory": ${var.memory},
      "cpu": ${var.cpu},
      "networkMode": "awsvpc",
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "mixfast-cloudwatch-log-group",
          "awslogs-region": "us-east-2",
          "awslogs-stream-prefix": "mixfast",
          "awslogs-create-group": "true"
        }
      },
      "environment": [
        {
          "name": "DB_HOST",
          "value": "dbmixfast.cszhpmnoblh4.us-east-2.rds.amazonaws.com"
        },
        {
          "name": "DB_PASSWORD",
          "value": "q1w2e3r4"
        },
        {
          "name": "DB_SCHEMA",
          "value": "dbmixfast"
        },
        {
          "name": "DB_USER",
          "value": "mixfast"
        },
        {
          "name": "DB_PORT",
          "value": "3306"
        },
        {
          "name": "MERCADO_PAGO_TOKEN",
          "value": "TEST-2380996068417028-090223-5c491b74d2c2d766d02bbe8269ac4fe3-1398154307"
        },
        {
          "name": "MERCADO_PAGO_USER_ID",
          "value": "1398154307"
        },
        {
          "name": "MERCADO_PAGO_POS",
          "value": "MIXFASTCX01"
        }
      ]
    }
  ]
  DEFINITION
}

resource "aws_ecs_service" "mixfast_ecs_service" {
  name                 = "service-${var.name}"
  cluster              = var.ecs_cluster_name
  task_definition      = aws_ecs_task_definition.mixfast_ecs_task_definition.arn
  desired_count        = 1
  force_new_deployment = true
  launch_type          = "FARGATE"

  network_configuration {
    security_groups  = [aws_security_group.mixfast_security_group.id]
    subnets          = var.subnet_ids
    assign_public_ip = true
  }

  depends_on = [
    aws_ecs_task_definition.mixfast_ecs_task_definition
  ]

  health_check_grace_period_seconds = 300

  load_balancer {
    target_group_arn = "arn:aws:elasticloadbalancing:us-east-2:022874923015:targetgroup/mixfast-tg/2ca276babf442124"
    container_name   = "container-${var.name}"
    container_port   = var.port
  }

  lifecycle {
    ignore_changes = [
      desired_count
    ]
  }

  tags = var.tags
}

resource "aws_appautoscaling_target" "mixfast_appautoscaling_target" {
  max_capacity       = 3
  min_capacity       = 1
  resource_id        = "service/${var.ecs_cluster_name}/${aws_ecs_service.mixfast_ecs_service.name}"
  scalable_dimension = "ecs:service:DesiredCount"
  service_namespace  = "ecs"
}

resource "aws_appautoscaling_policy" "mixfast_appautoscaling_policy" {
  name               = "${var.name}_appautoscaling_scale_down"
  policy_type        = "StepScaling"
  resource_id        = aws_appautoscaling_target.mixfast_appautoscaling_target.resource_id
  scalable_dimension = aws_appautoscaling_target.mixfast_appautoscaling_target.scalable_dimension
  service_namespace  = aws_appautoscaling_target.mixfast_appautoscaling_target.service_namespace

  step_scaling_policy_configuration {
    adjustment_type         = "ChangeInCapacity"
    cooldown                = 60
    metric_aggregation_type = "Maximum"

    step_adjustment {
      metric_interval_upper_bound = 0
      scaling_adjustment          = -1
    }
  }
}