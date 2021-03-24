package lu.vallis.config;

import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.util.ParsingUtils;

import java.util.Iterator;
import java.util.List;

public class UpperCaseWithUnderscoreFieldNamingStrategy implements FieldNamingStrategy {
    @Override
    public String getFieldName(PersistentProperty<?> property) {
        System.out.println("ta minh tu");
        //There are ready-made tools, why not?
        List<String> parts= ParsingUtils.splitCamelCaseToLower(property.getName());
        StringBuilder sb=new StringBuilder();
        Iterator it=parts.iterator();
        if(it.hasNext()){
            sb.append(it.next().toString().toUpperCase());//Change to uppercase as needed.
            while (it.hasNext()){
                sb.append("_");
                sb.append(it.next().toString().toUpperCase());//Change to uppercase as needed.
            }
        }
        return sb.toString();
    }


}
