package annotation.s5;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 18:13:07
 */
public class DateValidator
{

}
/*
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.util.Util;

@UnderConstruction(owner="Navin Gujarish")
public class DateValidator implements Validator{

        public void validate(FacesContext context, UIComponent component, Object value)
                        throws ValidatorException
        {
                String date = (String) value;
                String errorLabel = "Please enter a valid date.";
                if(!component.getAttributes().isEmpty())
                {
                        errorLabel = (String) component.getAttributes().get("errordisplayval");
                }

                if(!Util.validateAGivenDate(date))
                {
                        @Unfinished(changedBy = "Steve"
                                ,value="whether to add message to context or not, confirm"
                                ,priority=Priority.HIGH
                        )
                        FacesMessage message = new FacesMessage();
                        message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        message.setSummary(errorLabel);
                        message.setDetail(errorLabel);
                        throw new ValidatorException(message);
                }

        }
}
*/
