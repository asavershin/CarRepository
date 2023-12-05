package asavershin.car.dto.car;

import asavershin.car.dao.entities.QCar;
import asavershin.car.dto.PageDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Builder
public class CarFilter extends PageDto {
    @Min(value = 0,message = "Возвраст не может быть меньше 0")
    private Integer age;
    @Size(max = 50, message = "Максимальная длина 50 символов")
    private String country;
    @Size(max = 20,message = "Слишком длинное название цвета")
    private String color;
    public Predicate toPredicate() {
        var carFilter = QCar.car;
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(age)) {
            predicates.add(carFilter.owner.age.gt(age));
        }

        if (isNotBlank(color)) {
            predicates.add(carFilter.color.eq(color));
        }

        if (isNotBlank(country)) {
            predicates.add(carFilter.autoservice.country.eq(country));
        }


        return ExpressionUtils.allOf(predicates);
    }
}
