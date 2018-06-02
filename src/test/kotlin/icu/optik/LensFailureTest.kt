package icu.optik

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class LensFailureTest {
    private val unsupported = Unsupported(Meta(true, "unsupported", ParamMeta.BooleanParam, "name"))
    private val invalid = Invalid(Meta(true, "invalid", ParamMeta.BooleanParam, "name"))
    private val missing = Missing(Meta(true, "missing", ParamMeta.BooleanParam, "name"))

    @Test
    fun `overall returns invalid if there are no failures`() {
        assertThat(LensFailure().overall(), equalTo(Failure.Type.Invalid))
    }

    @Test
    fun `overall returns unsupported if there any unsupported failures`() {
        assertThat(LensFailure(unsupported, invalid, missing).overall(), equalTo(Failure.Type.Unsupported))
    }

    @Test
    fun `overall returns invalid if there only invalid and missing failures`() {
        assertThat(LensFailure(unsupported, invalid, missing).overall(), equalTo(Failure.Type.Unsupported))
    }

    @Test
    fun `overall returns missing if there no invalid for unsupported ailures`() {
        assertThat(LensFailure(missing, missing).overall(), equalTo(Failure.Type.Missing))
    }

    @Test
    fun `failures have descriptive toString`() {
        assertThat(missing.toString(), equalTo("missing 'name' is required"))
        assertThat(invalid.toString(), equalTo("invalid 'name' must be boolean"))
        assertThat(unsupported.toString(), equalTo("unsupported 'name' is not acceptable"))
    }
}