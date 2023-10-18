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
  default = "vpc-0181acb94f226ea16"
}
variable "subnet_ids" {
  type = list
  default = ["subnet-047e1b47592e49bba", "subnet-025586a415b8313b0", "subnet-0348cbb959217c711"]
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