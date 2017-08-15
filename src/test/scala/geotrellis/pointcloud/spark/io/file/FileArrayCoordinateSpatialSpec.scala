package geotrellis.pointcloud.spark.io.file

import io.pdal._

import geotrellis.pointcloud.spark._
import geotrellis.pointcloud.spark.io._
import geotrellis.spark.{LayerId, SpatialKey, TileLayerMetadata}
import geotrellis.spark.io._
import geotrellis.spark.io.file._
import geotrellis.spark.io.index._
import geotrellis.spark.testkit.io._
import geotrellis.spark.testkit.testfiles.TestFiles

import com.vividsolutions.jts.geom.Coordinate

class FileArrayCoordinateSpatialSpec
  extends PersistenceSpec[SpatialKey, Array[Coordinate], TileLayerMetadata[SpatialKey]]
    with SpatialKeyIndexMethods
    with PointCloudTestEnvironment
    with TestFiles
    with PointCloudSpatialTestFiles {

  lazy val reader = FileLayerReader(outputLocalPath)
  lazy val creader = FileCollectionLayerReader(outputLocalPath)
  lazy val writer = FileLayerWriter(outputLocalPath)
  lazy val deleter = FileLayerDeleter(outputLocalPath)
  lazy val copier = FileLayerCopier(outputLocalPath)
  lazy val mover  = FileLayerMover(outputLocalPath)
  lazy val reindexer = FileLayerReindexer(outputLocalPath)
  lazy val updater = FileLayerUpdater(outputLocalPath)
  lazy val tiles = FileValueReader(outputLocalPath)
  lazy val sample = pointCloudSampleC

  describe("Filesystem layer names") {
    it("should not throw with bad characters in name") {
      val layer = sample
      val layerId = LayerId("Some!layer:%@~`{}id", 10)

      println(outputLocalPath)
      writer.write[SpatialKey, Array[Coordinate], TileLayerMetadata[SpatialKey]](layerId, layer, ZCurveKeyIndexMethod)
      val backin = reader.read[SpatialKey, Array[Coordinate], TileLayerMetadata[SpatialKey]](layerId)
    }
  }
}