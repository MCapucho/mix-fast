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
  default = "vpc-037ba6b40e5c0940e"
}
variable "subnet_ids" {
  type = list
  default = ["subnet-06f7a7892cd3eff1e", "subnet-00cba77ae10a02240", "subnet-0b2aecbce1649de68"]
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
  default = "arn:aws:elasticloadbalancing:us-east-1:211125470560:targetgroup/mixfast-target-group/b5e774d9668bae48"
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