package ru.sd351.market.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WinterMarketCoreApplication {

	// Домашнее задание:
	// - Если пользователь не залогинен, то ему должна быть выдана общая корзина
	// - Если пользователь залогинен, то ему должна быть выдана его собственная корзина
	// - Добавить пагинацию на фронт (кнопки < Пред стр, 1, 2, 3, 4, .., След стр >)ию)

	public static void main(String[] args) {
		SpringApplication.run(WinterMarketCoreApplication.class, args);
	}
}
