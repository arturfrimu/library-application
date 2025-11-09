SHELL := /bin/bash
INFRA_SERVICES ?= library-postgres

PROJECT_NAME ?= library-platform
COMPOSE_FILE ?= docker-compose.yml
DC := docker compose -p $(PROJECT_NAME) -f $(COMPOSE_FILE)

.DEFAULT_GOAL := help

.PHONY: help
help: ## help
	@echo "Available commands:"
	@awk 'BEGIN {FS = ":.*##"} /^[a-zA-Z0-9_.%-]+:.*##/ { printf "  \033[36m%-16s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)

.PHONY: up
up: ## up
	$(DC) up -d --build

.PHONY: nuke
nuke: ## nuke
	$(DC) down -v

.PHONY: run
run: ## Clean, package and run Spring Boot application
	mvn clean package
	java -jar target/library-0.0.1-SNAPSHOT.jar