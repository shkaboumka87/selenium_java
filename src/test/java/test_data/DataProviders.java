package test_data;

import org.testng.annotations.DataProvider;

public class DataProviders {

    /**
     * Combination of invalid login credentials
     *
     * @return {Object[][]}
     */
    @DataProvider (name = "invalidCredentials")
    public Object[][] invalidCredentials() {

        return new Object[][] {
                {"standard_userrrr", "secret_sauce"},
                {"standard_user", "secret_saucee"},
                {"", "secret_sauce"},
                {"standard_user", ""}
        };
    }
}
