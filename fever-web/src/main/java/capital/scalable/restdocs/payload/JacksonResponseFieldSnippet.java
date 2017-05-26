package capital.scalable.restdocs.payload;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by fanfever on 2017/5/26.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 *
 * override support page
 */
public class JacksonResponseFieldSnippet extends AbstractJacksonFieldSnippet {
    public JacksonResponseFieldSnippet() {
        super("response-fields");
    }

    protected Type getType(final HandlerMethod method) {
        Class returnType = method.getReturnType().getParameterType();
        return (Type)(returnType == ResponseEntity.class?this.firstGenericType(method.getReturnType()):(returnType == Page.class?this.firstGenericType(method.getReturnType()):(this.isCollection(returnType)?new GenericArrayType() {
            public Type getGenericComponentType() {
                return JacksonResponseFieldSnippet.this.firstGenericType(method.getReturnType());
            }
        }:("void".equals(returnType.getName())?null:returnType))));
    }

    protected void enrichModel(Map<String, Object> model, HandlerMethod handlerMethod) {
        model.put("isPagedResponse", Boolean.valueOf(handlerMethod != null && this.isPageResponse(handlerMethod)));
    }

    private boolean isPageResponse(HandlerMethod handlerMethod) {
        Annotation[][] parameterAnnotations = handlerMethod.getMethod().getParameterAnnotations();
        return Arrays.stream(parameterAnnotations).flatMap(Arrays::stream).anyMatch(i -> i.toString().contains("page"));
    }

    public String getHeader() {
        return "Response fields";
    }
}
