package com.sonumaddheshiya.journalapk.service;

import com.sonumaddheshiya.journalapk.entity.UsersDetails;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.ParameterDeclarations;

import java.util.stream.Stream;

public class UserAgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
       return Stream.of(
               Arguments.of(
                       UsersDetails.builder().username("vivek").password("vivek").build(),
                       UsersDetails.builder().username("vishal").password(" ").build()
               )
       );
    }
}
