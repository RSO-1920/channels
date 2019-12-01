package si.fri.rso.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("rest-config")
public class ChannelConfigProperties {
    @ConfigValue(value = "file-storage-create-bucket", watch = true)
    private String fileStorageCreateBucketURI;

    public String getFileStorageCreateBucketURI() {
        return fileStorageCreateBucketURI;
    }

    public void setFileStorageCreateBucketURI(String fileStorageCreateBucketURI) {
        this.fileStorageCreateBucketURI = fileStorageCreateBucketURI;
    }
}
