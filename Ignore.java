public void verifyDownloadStart() throws InterruptedException {
  ChromeDriver driver = (ChromeDriver) webDriver.getWrappedDriver(); // Assuming using ChromeDriver
  ChromeDriverNetworkInterceptor interceptor = new ChromeDriverNetworkInterceptor(driver);
  interceptor.start();
  // Perform download action (clicking link)

  // Wait for a short duration to allow download initiation
  Thread.sleep(2000); // Adjust wait time as needed

  List<NetworkEvent> events = interceptor.getNetworkEvents();
  interceptor.stop();

  // Check if any network events indicate a download request
  boolean downloadStarted = events.stream().anyMatch(event ->
      event.getRequest().getUrl().endsWith(".pdf") && // Adjust for expected file extension
      event.getType().equals(NetworkEventType.DocumentRequest));

  Assert.assertTrue(downloadStarted, "Download did not start");
}
