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

  depends_on = [
    aws_appautoscaling_target.mixfast_appautoscaling_target
  ]
}