class DiskDump {

    public String parseArg(String arg) {
        String[] parts = arg.split("=");
        return parts[1];
    }

    public static void main(String[] args) {
        // Check args
        if (args.length != 4) {
            for (String arg : args) {
                if (arg.contains("=") == false) {
                    System.out.println("Usage: java DiskDump <inputFile> <outputFile> <skip> <blockSize>");
                    System.exit(0);
                }
            }
        } else {
            // Get and parse args
            DiskDump dd = new DiskDump();

            String inputFile = dd.parseArg(args[0]);
            String outputFile = dd.parseArg(args[1]);
            int skip = Integer.parseInt(dd.parseArg(args[2]));
            int blockSize = Integer.parseInt(dd.parseArg(args[3]));
        }
    }
}