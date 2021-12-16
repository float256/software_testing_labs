import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Product {
    @SerializedName("id")
    private int id;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("title")
    @NonNull
    private String title = "";

    @SerializedName("alias")
    @NonNull
    private String alias = "";

    @SerializedName("content")
    @NonNull
    private String content = "";

    @SerializedName("price")
    private int price;

    @SerializedName("old_price")
    private int oldPrice;

    @SerializedName("status")
    private int status;

    @SerializedName("keywords")
    @NonNull
    private String keywords = "";

    @SerializedName("description")
    @NonNull
    private String description = "";

    @SerializedName("hit")
    private int hit;
}
