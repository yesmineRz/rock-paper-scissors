package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import src.dto.response.DtoGetMultipleRoundResultResponse;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayMultipleRoundsTest {

	@Value("${rounds.number}")
	private int roundsNumber;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void getShouldReturn200AndResponseBodyWithValidValues() throws Exception {

		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/multiple"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		DtoGetMultipleRoundResultResponse body = mapper.readValue(result.getResponse().getContentAsString(), DtoGetMultipleRoundResultResponse.class);

		Assert.assertEquals(body.ties+body.winsPlayerA+body.winsPlayerB, roundsNumber);

	}


}
