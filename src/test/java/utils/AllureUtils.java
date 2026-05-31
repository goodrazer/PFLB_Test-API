package utils;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import java.io.File;
import java.io.IOException;

public final class AllureUtils {

    private AllureUtils() {}

    @Attachment(value = "screenshot", type = "image/png")
    public static byte[] takeScreenshot() {
        try {
            File screenshot = Screenshots.takeScreenShotAsFile();
            if (screenshot != null) {
                return Files.toByteArray(screenshot);
            }
        } catch (IOException e) {
            System.err.println("Не удалось создать скриншот: " + e.getMessage());
        }
        return new byte[0];
    }
}
