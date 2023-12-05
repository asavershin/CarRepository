package asavershin.car.controllers;

import asavershin.car.dto.autoservice.RequestAutoservice;
import asavershin.car.dto.autoservice.ResponseAutoservice;
import asavershin.car.mappers.AutoserviceMapper;
import asavershin.car.services.AutoserviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autoservice")
@RequiredArgsConstructor
@Tag(name = "autoservice", description = "Работа с автосервисом")
public class AutoserviceController {

    private final AutoserviceService autoserviceService;
    private final AutoserviceMapper autoserviceMapper;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAutoservice.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = {})
    })
    @Operation(description = "Создание автосервиса")
    public ResponseAutoservice createAutoservice(@Valid @RequestBody RequestAutoservice request){
        return autoserviceMapper
                .autoserviceToResponseAutoservice(autoserviceService
                        .createAutoservice(autoserviceMapper.requestAutoserviceToAutoservice(request)));
    }

    @GetMapping("/{autoserviceId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAutoservice.class))}),
            @ApiResponse(responseCode = "404", description = "Салон с id autoserviceId не найден", content = {})
    })
    @Operation(description = "Получение салона")
    public ResponseAutoservice getAutoservice(@PathVariable Long autoserviceId){
        return autoserviceMapper
                .autoserviceToResponseAutoservice(autoserviceService.getAutoservice(autoserviceId));
    }

    @PutMapping("/{autoserviceId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAutoservice.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = {}),
            @ApiResponse(responseCode = "404", description = "Салон с id autoserviceId не найден", content = {})
    })
    @Operation(description = "Обновление салона")
    public ResponseAutoservice updateAutoservice(@PathVariable Long autoserviceId,
                                                 @Valid @RequestBody RequestAutoservice request){
        return autoserviceMapper
                .autoserviceToResponseAutoservice(autoserviceService
                        .updateAutoservice(autoserviceId, request.getName(),
                request.getAddress(), request.getCountry()));
    }

    @DeleteMapping("/{autoserviceId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {}),
            @ApiResponse(responseCode = "404", description = "Салон с id autoserviceId не найден", content = {})
    })
    @Operation(description = "Удаление салона")
    public void deleteAutoservice(@PathVariable Long autoserviceId){
        autoserviceService.deleteAutoservice(autoserviceId);
    }

}
