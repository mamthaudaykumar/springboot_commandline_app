package com.motilent.entrolytics_notifier.fileutility;

import java.io.IOException;

public interface FileReader {

    String readFile(String path) throws IOException;
}
