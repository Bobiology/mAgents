package mglobe.africa.io.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mglobe.africa.io.web.rest.TestUtil;

public class UsersTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Users.class);
        Users users1 = new Users();
        users1.setId("id1");
        Users users2 = new Users();
        users2.setId(users1.getId());
        assertThat(users1).isEqualTo(users2);
        users2.setId("id2");
        assertThat(users1).isNotEqualTo(users2);
        users1.setId(null);
        assertThat(users1).isNotEqualTo(users2);
    }
}
