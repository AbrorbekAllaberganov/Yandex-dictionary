package zako.dictionary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tr {
    private String text;
    private String pos;
    private Syn []syn;
    private Mean []mean;
    private Ex []ex;
}
