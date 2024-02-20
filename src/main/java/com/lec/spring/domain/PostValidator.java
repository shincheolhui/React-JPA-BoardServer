package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PostValidator implements Validator {

    // Validator 인터페이스의 supports 메서드를 오버라이드합니다.
    // 이 메서드는 이 Validator가 어떤 클래스를 지원할지를 결정합니다.
    // 주어진 클래스(clazz)가 Post 클래스나 그 하위 클래스인지를 판단하여 그 결과를 반환합니다.
    // 지원한다면 validate 메서드가 호출되어 유효성 검사가 진행됩니다.
    @Override
    public boolean supports(Class<?> clazz) {
        // 디버깅을 위해 현재 supports 메서드가 어떤 클래스를 지원하는지를 콘솔에 출력합니다.
        System.out.println("supports 메서드가 지원하는 클래스는? => " + clazz.getName());

        //clazz가 Post 클래스나 그의 하위 클래스인지를 판단하여 그 결과를 result 변수에 저장합니다.
        boolean result = Post.class.isAssignableFrom(clazz);

        //디버깅을 위해 현재 클래스가 Post 클래스나 그의 하위 클래스인지 여부를 콘솔에 출력합니다.
        System.out.println("Post 클래스나 그의 하위 클래스인가? => " + result);

        return result;
    }

    // Validator 인터페이스의 validate 메서드를 오버라이드합니다.
    // 이 메서드는 주어진 객체(target)의 유효성을 검사하고, 유효하지 않을 경우 Errors 객체에 에러를 추가합니다.
    @Override
    public void validate(Object target, Errors errors) {
        // 디버깅을 위해 현재 validate 메서드가 호출되었음을 콘솔에 출력합니다.
        System.out.println("PostValidator 클래스의 validate() 메서드가 호출되었습니다.");

        // 주어진 객체(target)를 Post 클래스로 캐스팅하여 post 변수에 저장합니다.
        Post post = (Post) target;

        // post 객체에서 작성자(user)를 가져와 user 변수에 저장합니다.
        String user = post.getUser();

        // 작성자(user)가 null이거나 공백으로 이루어져 있는지를 검사합니다.
        // 직접적으로 user가 null이거나 공백인지를 조건문으로 확인한 후에, 필요한 에러를 직접 추가하고 있습니다.
        // 개발자가 직접 조건을 작성하여 유효성 검사를 수행하고자 하는 경우에 사용됩니다.
        // 이 방식은 더 많은 유연성을 제공하며, 특별한 조건이나 로직을 적용할 수 있습니다.
        if (user == null || user.trim().isEmpty()) {
            // rejectValue(field, errorCode) <- 유효성 검증 에러 추가

            // 디버깅을 위해 작성자가 비어있을 때 해당 메시지를 콘솔에 출력합니다.
            System.out.println("domain의 PostValidator클래스, validate()함수, if문 수행합니다.");

            // errors 객체에 작성자 필드("user")에 대한 에러를 추가합니다. 이때 메시지는 "작성자는 필수입니다."입니다.
            errors.rejectValue("user", "작성자는 필수입니다.");
        }

        // ValidationUtils 클래스를 사용하여 subject 필드가 비어있거나 공백으로 이루어져 있는지를 검사하고,
        // 필요할 경우 에러를 errors 객체에 추가합니다. 메시지는 "글 제목은 필수입니다."입니다.
        // 스프링 프레임워크에서 제공하는 ValidationUtils 클래스의 rejectIfEmptyOrWhitespace 메서드를 사용한 방식입니다.
        // 주어진 필드("subject")가 비어있거나 공백인 경우에 해당 필드에 대한 에러를 추가합니다.
        // 이 방식은 코드를 간결하게 유지하면서 스프링이 제공하는 유틸리티 메서드를 활용하여 일반적인 유효성 검사를 수행하고자 하는 경우에 사용됩니다.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "글 제목은 필수입니다.");

        // 두 방식은 기본적으로 동일한 목표를 가지고 있지만, 개발자의 선호나 특정 상황에 따라 선택하여 사용할 수 있습니다.
        // 간단한 유효성 검사의 경우에는 ValidationUtils를 사용하는 것이 편리하며, 더 복잡한 조건이 필요한 경우 직접 조건문을 사용하는 것이 적절할 수 있습니다.
    }
}
