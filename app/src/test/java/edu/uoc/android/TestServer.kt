package edu.uoc.android

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@RunWith(MockitoJUnitRunner::class)
class TestServer {
    private val BODY = """{
  "nom": "Museus",
  "machinename": "museus",
  "descripcio": "Museus municipals.",
  "paraules_clau": [
    "museus",
    "xarxa",
    "local",
    "municipals",
    "agenda",
    "activitats",
    "Barcelona"
  ],
  "llicencia": "https://creativecommons.org/licenses/by/4.0/deed.ca",
  "freq_actualitzacio": 30,
  "sector": [
    "cultura-ocio"
  ],
  "tema": [
    "cultura"
  ],
  "responsable": "Oficina de Patrimoni Cultural. Gerència de Serveis de Cultura",
  "idioma": "Català",
  "home_page": "http://museuslocals.diba.cat/",
  "referencies": [
    {
      "url": "http://museuslocals.diba.cat/",
      "nom": "Patrimoni cultural"
    }
  ],
  "tipus": "punt",
  "estat": "public",
  "creacio": "2014-06-10 09:34:08",
  "modificacio": "2019-04-25 09:47:03",
  "entitats": 166,
  "elements": [
    {
      "punt_id": "museu37",
      "adreca_nom": "Museu del Prat. Sala d'exposicions. Cèntric Espai Cultural",
      "descripcio": "El Museu del Prat treballa temes relacionats amb la història i el patrimoni de la ciutat. Actualment, disposa d'una sala d'exposicions temporals al Cèntric Espai Cultural.",
      "grup_adreca": {
        "adreca": "Pl. de Catalunya 39-41",
        "codi_postal": "08820",
        "municipi_nom": "Prat de Llobregat, El",
        "adreca_completa": "Pl. de Catalunya 39-41, 08820, PRAT DE LLOBREGAT, El"
      },
      "localitzacio": "41.3218808,2.0926533",
      "imatge": [
        "https://museuslocals.diba.cat/imgs/banners-museus/37/banner.jpg"
      ],
      "url_general": "http://www.patrimonicultural.elprat.cat",
      "email": [
        "patrimonicultural@elprat.cat"
      ],
      "telefon_contacte": [
        "934782858"
      ],
      "fax": [
        
      ],
      "horaris": "Dilluns, de 15.30 a 21 h.De dimarts a divendres, de 09 a 21 h. Dissabtes, de 10 a 20 h.",
      "rel_municipis": {
        "ine": "08169",
        "municipi_nom": "El Prat de Llobregat",
        "municipi_nom_curt": "Prat de Llobregat",
        "municipi_article": "El",
        "municipi_transliterat": "el_prat_de_llobregat",
        "municipi_curt_transliterat": "prat_de_llobregat",
        "centre_municipal": "41.3246333,2.0952568",
        "grup_comarca": {
          "comarca_codi": "11",
          "comarca_nom": "Baix Llobregat"
        },
        "grup_provincia": {
          "provincia_codi": "8",
          "provincia_nom": "Barcelona"
        },
        "grup_ajuntament": {
          "adreca-completa": "Pl. Vila, 1, 08820 Prat de Llobregat",
          "adreca": "Pl. Vila, 1",
          "codi_postal": "08820",
          "localitzacio": "41.3246333,2.0952568",
          "telefon_contacte": "933790050",
          "fax": "933793416",
          "email": "oiac@elprat.cat",
          "url_general": "https://www.elprat.cat",
          "cif": "P0816800G"
        },
        "municipi_escut": "https://media.diba.cat/diba/municipis/img/escuts/ec08169.png",
        "municipi_bandera": "",
        "municipi_vista": "https://media.diba.cat/diba/municipis/img/vistes/vista08169.jpg",
        "ine6": "081691",
        "nom_dbpedia": "El_Prat_de_Llobregat"
      },
      "rel_temes": {
        "count": 0,
        "elements": "3"
      },
      "tags": [
        "museu"
      ],
      "categoria": [
        "museu"
      ],
      "rel_comarca": [
        "11"
      ],
      "id_secundari": "",
      "cercador_codi": "",
      "director": "",
      "xarxes_socials": [
        
      ],
      "url_relacionades": [
        "http://museuslocals.diba.cat/museu/37"
      ],
      "inici_horari_hivern": "",
      "inici_horari_estiu": ""
    }
  ]
}"""
    private lateinit var mockServer: MockWebServer
    lateinit var mockResponse: MockResponse
    val PATH_MUSEUS = "/api/dataset/museus/"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.mockServer = MockWebServer()
        this.mockServer.start()

        this.mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(BODY)

        val dispatcher: Dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                when (request.path) {
                    PATH_MUSEUS -> return mockResponse
                }
                return MockResponse().setResponseCode(404)
            }
        }
        mockServer.dispatcher = dispatcher
    }

    @Test
    fun testMuseums() {
        val url: URL = mockServer.url(PATH_MUSEUS).toUrl()
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStream: InputStream = connection.inputStream
        val response: String = inputStream.reader().readText()
        //Assert.assertEquals(BODY, mockResponse.getBody()?.readUtf8())
        Assert.assertEquals(BODY, response)
    }
}