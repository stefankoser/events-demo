.PHONY: build blueprint local_install

#VERSION ?= 1.1.2
VERSION ?= latest
REPO ?= ateamsaas/stefan-ocir-public


build:
	# --network=host may be needed inside OCNA to allow external network access in combination with Cisco VPN
	docker build -t fra.ocir.io/$(REPO)/customerservice:$(VERSION) --network=host .
	# docker build -t phx.ocir.io/$(REPO)/customerservice:$(VERSION) .
	docker push fra.ocir.io/$(REPO)/customerservice:$(VERSION)

all: build

.DEFAULT_GOAL := all
