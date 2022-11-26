package com.caddy.erasxchange.repositories.form;

import com.caddy.erasxchange.models.forms.CourseTransferForm;
import com.caddy.erasxchange.models.forms.Form;
import com.caddy.erasxchange.repositories.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTransferFormRepository extends FormRepository<CourseTransferForm> {
}