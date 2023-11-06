variable "region" {
  default = "us-east-1"
}

variable "tags" {
  type = map(string)
  default     = {
    "name" : "mixfast"
    "company" : "fiap"
  }
}

variable "name" {
  type = string
  default = "mixfast"
}

variable "vpc_id" {
  default = "vpc-0a928f18f52e3f526"
}
variable "subnet_ids" {
  type = list
  default = ["subnet-0f1c1df7d6204cd14", "subnet-02360cad76c9230ab", "subnet-055459a695e1caddb"]
}
variable "network_mode" {
  type = string
  default = "awsvpc"
}
variable "cpu" {
  type = string
  default = "256"
}
variable "memory" {
  type = string
  default = "512"
}
variable "target_group_arn" {
  type = string
  default = "arn:aws:elasticloadbalancing:us-east-1:022874923015:targetgroup/mixfast-target-group/2a102fd3fd0daa69"
}
variable "port" {
  type = number
  default = 9080
}
variable "ecs_cluster_name" {
  type = string
  default = "mixfast_ecs_cluster"
}
variable "from_port_ingress" {
  type = number
  default = 9080
}
variable "to_port_ingress" {
  type = number
  default = 9080
}
variable "protocol_ingress" {
  type = string
  default = "TCP"
}
variable "from_port_egress" {
  type = number
  default = 0
}
variable "to_port_egress" {
  type = number
  default = 0
}
variable "protocol_egress" {
  type = string
  default = "-1"
}