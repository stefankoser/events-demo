/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oracle.examples.events.customers;

import com.oracle.examples.events.customers.model.Customer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

/**
 * Resource for managing users
 */
@Path("/customer")
@RequestScoped
public class CustomerResource {

    private final CustomerRepository customerRepository;

    @Context
    UriInfo uriInfo;

    @Inject
    public CustomerResource(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDefaultMessage() {
        return Response.ok(Map.of("OK", true)).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        Customer customer = customerRepository.get(id);
        if( customer != null ) {
            return Response.ok(customer).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        return Response.ok(this.customerRepository.findAll()).build();
    }

    @Path("/list/{offset}/{max}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsersPaginated(@PathParam("offset") int offset, @PathParam("max") int max) {
        return Response.ok(this.customerRepository.findAll(offset, max)).build();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCustomer(Customer customer) {
        Set<ConstraintViolation<Customer>> violations = customerRepository.validate(customer);

        if( violations.size() == 0 ) {
            customerRepository.save(customer);
            return Response.created(
                    uriInfo.getBaseUriBuilder()
                            .path("/customer/{id}")
                            .build(customer.getId())
            ).build();
        }
        else {
            List<HashMap<String, String>> errors = new ArrayList<>();

            violations.stream()
                    .forEach( (violation) -> {
                                Object invalidValue = violation.getInvalidValue();
                                HashMap<String, String> errorMap = new HashMap<>();
                                errorMap.put("field", violation.getPropertyPath().toString());
                                errorMap.put("message", violation.getMessage());
                                errorMap.put("currentValue", invalidValue == null ? null : invalidValue.toString());
                                errors.add(errorMap);
                            }
                    );

            return Response.status(422)
                    .entity(Map.of( "validationErrors", errors ))
                    .build();
        }

    }

    @Path("{id}")
    @DELETE
    public Response deleteCustomer(@PathParam("id") String id) {
        customerRepository.deleteById(id);
        return Response.noContent().build();
    }


}
