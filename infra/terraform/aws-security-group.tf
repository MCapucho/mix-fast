resource "aws_security_group" "mixfast_security_group" {
  name        = "${var.name}_ecs_security-group"
  description = "Grupo de seguranca da aplicacao mixfast"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = var.from_port_ingress
    to_port     = var.to_port_ingress
    protocol    = var.protocol_ingress
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = var.from_port_egress
    to_port     = var.to_port_egress
    protocol    = var.protocol_egress
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = var.tags
}