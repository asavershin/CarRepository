package asavershin.car.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;

    private List<T> content;
}
