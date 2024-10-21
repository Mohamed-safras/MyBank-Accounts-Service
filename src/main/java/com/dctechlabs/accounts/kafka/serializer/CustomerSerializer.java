package com.dctechlabs.accounts.kafka.serializer;

import com.dctechlabs.accounts.entity.Customer;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class CustomerSerializer implements Serializer<Customer> {

    /**
     * @param s
     * @param data
     * @return
     */
    @Override
    public byte[] serialize(String s, Customer data) {
        // Serialize the Customer object and include additional metadata
        // Example: customerId|customerName|CustomerEmail|mobileNumber
        String serializedData = data.getCustomerId() + "|" + data.getCustomerEmail() + "|" + data.getCustomerName() + "|" + data.getMobileNumber();

        return serializedData.getBytes(StandardCharsets.UTF_8);
    }
}
