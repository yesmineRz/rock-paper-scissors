package src;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import src.dto.request.DtoPostSingleRoundResultRequest;
import src.dto.response.DtoPostSingleRoundResultResponse;
import src.tools.ChoiceEnum;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlaySingleRoundTest {


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


	private String convertJsonToString(DtoPostSingleRoundResultRequest request) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(request);
		return requestJson;
	}

	private DtoPostSingleRoundResultRequest buildRequestBody(Character choiceA, Character choiceB){
		DtoPostSingleRoundResultRequest request = new DtoPostSingleRoundResultRequest();
		request.choiceA = choiceA!=null ? ChoiceEnum.valueOf(choiceA.toString()): null;
		request.choiceB = choiceB!=null ? ChoiceEnum.valueOf(choiceB.toString()): null;

		return request;
	}

	@Test
	public void postWithEmptyBodyShouldReturn400() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/single"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	public void postWithNullValuesShouldReturn400() throws Exception {

		DtoPostSingleRoundResultRequest request = buildRequestBody(null,null);
		String requestString = convertJsonToString(request);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/single")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.valueOf(requestString)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}


	@Test
	public void postRockAndPaperShouldReturn200AndWinnerIsPlayerB() throws Exception {

		DtoPostSingleRoundResultRequest request = buildRequestBody('R','P');
		String requestString = convertJsonToString(request);

		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/single")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.valueOf(requestString)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		DtoPostSingleRoundResultResponse body = mapper.readValue(result.getResponse().getContentAsString(), DtoPostSingleRoundResultResponse.class);

		Assert.assertEquals(body.winner, 2);
	}

	@Test
	public void postPaperAndRockShouldReturn200AndWinnerIsPlayerA() throws Exception {

		DtoPostSingleRoundResultRequest request = buildRequestBody('P','R');
		String requestString = convertJsonToString(request);

		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/single")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.valueOf(requestString)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		DtoPostSingleRoundResultResponse body = mapper.readValue(result.getResponse().getContentAsString(), DtoPostSingleRoundResultResponse.class);

		Assert.assertEquals(body.winner, 1);
	}

	@Test
	public void postRockAndRockShouldReturn200AndResultIsTie() throws Exception {

		DtoPostSingleRoundResultRequest request = buildRequestBody('R','R');
		String requestString = convertJsonToString(request);

		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/single")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.valueOf(requestString)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		DtoPostSingleRoundResultResponse body = mapper.readValue(result.getResponse().getContentAsString(), DtoPostSingleRoundResultResponse.class);

		Assert.assertEquals(body.winner, 0);
	}

}
