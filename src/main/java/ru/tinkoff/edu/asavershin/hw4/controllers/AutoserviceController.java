package ru.tinkoff.edu.asavershin.hw4.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseAutoservice;
import ru.tinkoff.edu.asavershin.hw4.dto.RequestAutoservice;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseCar;
import ru.tinkoff.edu.asavershin.hw4.mappers.AutoserviceMapper;
import ru.tinkoff.edu.asavershin.hw4.services.AutoserviceService;

import java.util.List;

@RestController
@RequestMapping("/autoservice")
@RequiredArgsConstructor
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
    @Description("Создание автосервиса")
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
    @Description("Получение салона")
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
    @Description("Обновление салона")
    public ResponseAutoservice updateAutoservice(@PathVariable Long autoserviceId,
                                                 @Valid @RequestBody RequestAutoservice request){
        return autoserviceMapper
                .autoserviceToResponseAutoservice(autoserviceService
                        .updateAutoservice(autoserviceId, request.getName(),
                request.getAddress(), request.getCountry()));
    }

}
