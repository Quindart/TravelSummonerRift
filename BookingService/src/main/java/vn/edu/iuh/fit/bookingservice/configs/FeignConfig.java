package vn.edu.iuh.fit.bookingservice.configs;

import feign.Client;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.iuh.fit.bookingservice.exception.errors.BadRequestException;
import vn.edu.iuh.fit.bookingservice.exception.errors.NotFoundException;

import java.io.IOException;

//@Configuration
//public class FeignConfig {
//
//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new CustomErrorDecoder();
//    }
//
//    public class CustomErrorDecoder implements ErrorDecoder {
//        @Override
//        public Exception decode(String methodKey, Response response) {
//            switch (response.status()) {
//                case 400:
//                    return new BadRequestException();
//                case 302:
//                    re Util.toString(response.body().asReader());
//                default:
//                    return new Exception("Generic error");
//            }
//        }
//    }
//}
