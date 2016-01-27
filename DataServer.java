class DataServer {
	private static String fileName;
	public static void setSelectedFileName(String fileName) {
		DataServer.fileName = fileName;
	}
	public static String getSelectedFileName() {
		return fileName;
	}
}
