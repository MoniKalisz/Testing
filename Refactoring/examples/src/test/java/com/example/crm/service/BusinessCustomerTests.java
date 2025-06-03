class BusinessCustomerTests extends BaseCustomerTest {

    @Test
    void shouldRegisterCustomerWithValidEmail() {
        // given
        CustomerService service = serviceWithFakeRepoAndDummyNotifier();
        Customer customer = createCustomer("55");

        // when
        service.registerCustomer(customer);

        // then
        // Tu dodajemy tylko logikę biznesową – reszta jest reużywalna
        assertEquals("user55@example.com", customer.getEmail());
    }
}
