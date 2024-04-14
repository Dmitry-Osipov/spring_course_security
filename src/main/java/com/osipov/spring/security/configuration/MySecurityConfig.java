package com.osipov.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

// Класс отвечает за конфигурацию Spring Security
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;  // Работает с помощью библиотек mysql-connector-j и c3p0

    @Autowired
    public MySecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Для версий Spring 6 также может потребоваться создание отдельного бина new BCryptPasswordEncoder(), а для метода
    // ниже следует использовать бин UserDetailsService, где в теле метода фигурирует всё то же самое, кроме замены
    // UserBuilder на InMemoryUserDetailsManager + можно будет писать не User.withDefaultPasswordEncoder, а просто
    // new InMemoryUserDetailsManager().
    // Для создания BCryptPasswordEncoder с хранением данных в БД следует прописывать такой бин:
    // @Bean
    // public UserDetailsManager userDetailsManager(DataSource dataSource) {
    //     return new JdbcUserDetailsManager(dataSource);
    // }
    // Ссылка на документацию:
    // https://docs.spring.io/spring-security/reference/servlet/configuration/java.html#jc-hello-wsca

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);

        // Хранение пользователей и паролей в памяти:
//        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//        auth.inMemoryAuthentication()
//                .withUser(userBuilder.username("dmitry").password("dmitry").roles("EMPLOYEE"))
//                .withUser(userBuilder.username("elena").password("elena").roles("HR"))
//                .withUser(userBuilder.username("ivan").password("ivan").roles("MANAGER", "HR"));
    }

    // Для версий Spring 6 система авторизации немного другая:
    // @Bean
    //public UserDetailsService userDetailsService(){
    //
    //  UserDetails user1 = User.withDefaultPasswordEncoder()
    //                              .username("Danil")
    //                              .password("Danil")
    //                              .roles("IT")
    //                              .build();
    //
    //  UserBuilder users = User.withDefaultPasswordEncoder();
    //
    //  UserDetails user = users
    //                          .username("user")
    //                          .password("user")
    //                          .roles("EMPLOYEE")
    //                          .build();
    //
    //  UserDetails hr = users
    //                      .username("hr")
    //                      .password("hr")
    //                      .roles("HR")
    //                      .build();
    //
    //  UserDetails manager = users
    //                          .username("manager")
    //                          .password("manager")
    //                          .roles("MANAGER", "HR")
    //                          .build();
    //
    //  return new InMemoryUserDetailsManager(user, hr, manager, user1);
    //}
    //
    //@Bean
    //public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //  http.authorizeHttpRequests((user) -> user
    //                  .requestMatchers(new AntPathRequestMatcher("/")).hasAnyRole("HR", "MANAGER", "IT", "EMPLOYEE")
    //                  .requestMatchers(new AntPathRequestMatcher("/manager_info/**")).hasRole("MANAGER")
    //                  .requestMatchers(new AntPathRequestMatcher("/hr_info/**")).hasRole("HR")
    //                  .anyRequest().authenticated()
    //                  ).formLogin(Customizer.withDefaults());
    //
    //
    //  return http.build();
    //}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
                .antMatchers("/hr_info").hasRole("HR")
                .antMatchers("/manager_info/**").hasRole("MANAGER") // Добавляя звёздочки, мы указываем, что у
                // человека с ролью MANAGER будет доступ ко всем адресам, начинающимся с manager_info
                .and().formLogin().permitAll();  // Указываем, что запрашиваемо форму логина и пароля у всех
    }
}
