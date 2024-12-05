package controllers


import ie.setu.models.Team
import ie.setu.models.Player
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File

class ManagerAPITest {


    private var liverpool: Team? = null
    private var manUnited: Team? = null
    private var chelsea: Team? = null
    private var salah: Player? = null
    private var rashford: Player? = null
    private var palmer: Player? = null
    private var populatedTeams: ManagerAPI? = ManagerAPI(XMLSerializer(File("teams.xml")))
    private var emptyTeams: ManagerAPI? = ManagerAPI(XMLSerializer(File("empty-teams.xml")))

    @BeforeEach
    fun setup() {
        liverpool = Team("Liverpool", "Arne Slot", "Moh Salah", "Premier League", 10)
        manUnited = Team("Manchester United", "Marc Skinner", "Harry Maguire", "Premier League", 8)
        chelsea = Team("Chelsea", "Enzo Maresca", "Reece James", "Premier League", 9)
        salah = Player("Salah", 11, 1.9, 75.9, "Forward", "Epytian")
        rashford = Player("Rashford", 22, 1.8, 85.0, "Midfielder", "English")
        palmer = Player("Palmer", 3, 1.9, 82.9, "Forward", "English")

        liverpool?.addPlayer(salah!!)
        manUnited?.addPlayer(rashford!!)
        chelsea?.addPlayer(palmer!!)

        populatedTeams?.addTeam(liverpool!!)
        populatedTeams?.addTeam(manUnited!!)
        populatedTeams?.addTeam(chelsea!!)
    }

    @AfterEach
    fun tearDown() {
        liverpool = null
        manUnited = null
        chelsea = null
        salah = null
        rashford = null
        palmer = null
        populatedTeams = null
        emptyTeams = null
    }

    @Nested
    inner class AddTeams {
        @Test
        fun `adding a Team to a populated list adds to ArrayList`() {
            val newTeam = Team("Arsenal", "Mikel Arteta", "Saka", "Premier League", 13)
            assertEquals(3, populatedTeams!!.numberOfTeams())
            assertTrue(populatedTeams!!.addTeam(newTeam))
            assertEquals(4, populatedTeams!!.numberOfTeams())
            assertEquals(newTeam, populatedTeams!!.findTeam(populatedTeams!!.numberOfTeams() - 1))
        }
    }

    @Nested
    inner class RemoveTeams {
        @Test
        fun `removing a Team that does not exist, returns null`() {
            assertNull(emptyTeams!!.removeTeam(0))
            assertNull(populatedTeams!!.removeTeam(-1))
            assertNull(populatedTeams!!.removeTeam(3))
        }

        @Test
        fun `removing a team that exists and returns deleted object`() {
            assertEquals(3, populatedTeams!!.numberOfTeams())
            assertEquals(chelsea, populatedTeams!!.removeTeam(2))
            assertEquals(2, populatedTeams!!.numberOfTeams())
            assertEquals(liverpool, populatedTeams!!.removeTeam(0))
            assertEquals(1, populatedTeams!!.numberOfTeams())
        }
    }

    @Nested
    inner class ListTeams {
        @Test
        fun `listTeam returns No Teams Stored message when ArrayList is empty`() {
            assertEquals(0, emptyTeams!!.numberOfTeams())
            assertTrue(emptyTeams!!.listTeam().lowercase().contains("no teams found"))
        }

        @Test
        fun `listTeam returns Teams when ArrayList has teams stored`() {
            assertEquals(3, populatedTeams!!.numberOfTeams())
            val teamsString = populatedTeams!!.listTeam().lowercase()
            assertTrue(teamsString.contains("liverpool"))
            assertTrue(teamsString.contains("manchester united"))
            assertTrue(teamsString.contains("chelsea"))
            assertTrue(teamsString.contains("arne slot"))
            assertTrue(teamsString.contains("moh salah"))
            assertTrue(teamsString.contains("harry maguire"))
            assertTrue(teamsString.contains("reece james"))
            assertTrue(teamsString.contains("premier league"))

        }
    @Nested
    inner class UpdateTeams {
        @Test
        fun `updating a team that does not exist returns false`() {
            assertFalse(populatedTeams!!.updateTeam(3,
            Team("Arsenal", "Mikel Arteta", "Saka", "Premier League", 13)))
            assertFalse(populatedTeams!!.updateTeam(-1,
            Team("Arsenal", "Mikel Arteta", "Saka", "Premier League", 13)))
            assertFalse(emptyTeams!!.updateTeam(0, Team("Arsenal", "Mikel Arteta", "Saka", "Premier League", 13)))
            }

        @Test
        fun `updating a team that exists returns true and updates`() {
            assertEquals(chelsea, populatedTeams!!.findTeam(2))
            assertEquals("Chelsea", populatedTeams!!.findTeam(2)!!.tName)
            assertEquals("Enzo Maresca", populatedTeams!!.findTeam(2)!!.manager)
            assertEquals("Reece James", populatedTeams!!.findTeam(2)!!.captain)
            assertEquals("Premier League", populatedTeams!!.findTeam(2)!!.league)
            assertEquals(9, populatedTeams!!.findTeam(2)!!.trophies)

            assertTrue(populatedTeams!!.updateTeam(2,
            Team("Chelsea", "Mauricio Pochettino", "Thiago Silva", "Premier League", 10)))
            assertEquals("Chelsea", populatedTeams!!.findTeam(2)!!.tName)
            assertEquals("Mauricio Pochettino", populatedTeams!!.findTeam(2)!!.manager)
            assertEquals("Thiago Silva", populatedTeams!!.findTeam(2)!!.captain)
            assertEquals("Premier League", populatedTeams!!.findTeam(2)!!.league)
            assertEquals(10, populatedTeams!!.findTeam(2)!!.trophies)
            }
        }

    @Nested
    inner class AddPlayers {
        @Test
        fun `adding a player to a team `() {
            val newPlayer = Player("Erling Haaland", 10, 1.8, 75.0, "Forward", "Norway")
            assertTrue(populatedTeams!!.addPlayerToTeam(liverpool!!, newPlayer))
            assertTrue(liverpool!!.players.contains(newPlayer))
        }
    }
    @Nested
    inner class RemovePlayer {
        @Test
        fun `removing a Player that does not exist, returns null`() {
            assertNull(emptyTeams!!.removePlayer(0))
            assertNull(populatedTeams!!.removePlayer(-1))
            assertNull(populatedTeams!!.removePlayer(3))
            }

        }
    @Nested
    inner class UpdatePlayers {
        @Test
        fun `updating a player that does not exist returns false`() {
            assertFalse(populatedTeams!!.updatePlayer(3,
            Player("Haaland", 9, 1.94, 88.0, "Forward", "Norwegian")))
            assertFalse(populatedTeams!!.updatePlayer(-1,
            Player("Haaland", 9, 1.94, 88.0, "Forward", "Norwegian")))
            assertFalse(emptyTeams!!.updatePlayer(0,
            Player("Haaland", 9, 1.94, 88.0, "Forward", "Norwegian")))
            }

        }
    @Nested
    inner class PersistenceTests {
        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {

            val storingTeams = ManagerAPI(XMLSerializer(File("TeamInformation.xml")))
            storingTeams.store()


            val loadedTeams = ManagerAPI(XMLSerializer(File("TeamInformation.xml")))
            loadedTeams.load()


            assertEquals(0, storingTeams.numberOfPlayers())
            assertEquals(0, loadedTeams.numberOfPlayers())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
            }

            @Test
            fun `saving and loading a loaded collection in XML doesn't lose data`() {

            val storingTeams = ManagerAPI(XMLSerializer(File("TeamInformation.xml")))
            storingTeams.addTeam(liverpool!!)
            storingTeams.addTeam(manUnited!!)
            storingTeams.addTeam(chelsea!!)
            storingTeams.store()


            val loadedTeams = ManagerAPI(XMLSerializer(File("TeamInformation.xml")))
            loadedTeams.load()


            assertEquals(3, storingTeams.numberOfTeams())
            assertEquals(3, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
            assertEquals(storingTeams.findTeam(0), loadedTeams.findTeam(0))
            assertEquals(storingTeams.findTeam(1), loadedTeams.findTeam(1))
            assertEquals(storingTeams.findTeam(2), loadedTeams.findTeam(2))

            }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {

            val storingTeams = ManagerAPI(JSONSerializer(File("teams.json")))
            storingTeams.store()


            val loadedTeams = ManagerAPI(JSONSerializer(File("teams.json")))
            loadedTeams.load()


            assertEquals(0, storingTeams.numberOfTeams())
            assertEquals(0, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
                }

        @Test
        fun `saving and loading a loaded collection in JSON doesn't lose data`() {

            val storingTeams = ManagerAPI(JSONSerializer(File("teams.json")))
            storingTeams.addTeam(liverpool!!)
            storingTeams.addTeam(manUnited!!)
            storingTeams.addTeam(chelsea!!)
            storingTeams.store()


            val loadedTeams = ManagerAPI(JSONSerializer(File("teams.json")))
            loadedTeams.load()


            assertEquals(3, storingTeams.numberOfTeams())
            assertEquals(3, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
            assertEquals(storingTeams.findTeam(0), loadedTeams.findTeam(0))
            assertEquals(storingTeams.findTeam(1), loadedTeams.findTeam(1))
            assertEquals(storingTeams.findTeam(2), loadedTeams.findTeam(2))
                }
            }
        }
    }










