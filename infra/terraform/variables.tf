variable "region" {
  default = "us-east-2"
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
  type = string
  default = "vpc-0cefae0a895536e11"
}
variable "subnet_ids" {
  type = list
  default = ["subnet-0153366087b2b87cf", "subnet-041626c494ced434c", "subnet-0752e39645ab26607"]
}
variable "cpu" {
  type = string
  default = "256"
}
variable "memory" {
  type = string
  default = "512"
}
variable "port" {
  type = number
  default = 9080
}
variable "ecs_cluster_name" {
  type = string
  default = "mixfast-ecs-cluster"
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