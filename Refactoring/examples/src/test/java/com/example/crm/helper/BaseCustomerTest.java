abstract class BaseCustomerTest {

    protected Customer createCustomer(String id) {
        return new Customer(id, "Test User", "user" + id + "@example.com");
    }

    protected FakeCustomerRepository fakeRepo() {
        return new FakeCustomerRepository();
    }

    protected DummyNotifier dummyNotifier() {
        return customer -> {};
    }

    protected CustomerService serviceWithFakeRepoAndDummyNotifier() {
        return new CustomerService(fakeRepo(), dummyNotifier());
    }
}
