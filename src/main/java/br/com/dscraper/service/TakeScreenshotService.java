package br.com.dscraper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class TakeScreenshotService {

    /**
     * take Screenshot.
     * @param url www.
     */
    public void takeScreenshot(String url) {

        try {
            String phantomjsExeutableFilePath = "/home/celio/Documents/phantomjs/bin/phantomjs";
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setJavascriptEnabled(true);

            caps.setCapability(
                    PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                    phantomjsExeutableFilePath);

            caps.setCapability("takesScreenshot", true);

            PhantomJSDriver driver = new PhantomJSDriver(caps);
            driver.manage().window().maximize();
            driver.get(url);

            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = "/home/celio/Documents/"
                    + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + ".jpeg";

            FileUtils.copyFile(scrFile, new File(fileName), true);

            driver.quit();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

}
