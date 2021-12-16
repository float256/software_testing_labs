import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("status")
    private int status;
}
