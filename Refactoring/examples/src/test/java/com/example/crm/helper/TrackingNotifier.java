class TrackingNotifier implements Notifier {
    private final Set<String> notifiedEmails = new HashSet<>();

    @Override
    public void sendWelcomeMessage(Customer customer) {
        notifiedEmails.add(customer.getEmail());
    }

    public boolean wasMessageSentTo(String email) {
        return notifiedEmails.contains(email);
    }
}
