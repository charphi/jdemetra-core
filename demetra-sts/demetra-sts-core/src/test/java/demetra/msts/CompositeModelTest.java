/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demetra.msts;

import demetra.data.DataBlock;
import demetra.maths.matrices.Matrix;
import demetra.timeseries.TsData;
import demetra.timeseries.TsPeriod;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jean Palate
 */
public class CompositeModelTest {
    
    public CompositeModelTest() {
    }

    public static final double[] Prod_B_C = {10.4988185, 10.0760473, 11.6966702, 10.7102041, 11.6262084, 12.3308271, 12.6126745, 13.6696026, 14.2332975, 15.1493018, 15.1493018, 13.7400644, 13.9514501, 13.5286788, 14.4446831, 14.8674544, 14.3742213, 15.2902256, 14.8674544, 15.2902256, 14.8674544, 16.7699248, 16.4880773, 14.5856069, 15.2197637, 14.515145, 15.4311493, 14.7265306, 15.4311493, 14.7265306, 16.0653061, 15.6425349, 17.1222342, 18.5314715, 17.2631579, 16.4880773, 15.6425349, 14.7265306, 17.1222342, 16.4880773, 15.8539205, 17.6154672, 18.0382385, 17.4040816, 18.9542427, 20.3634801, 19.1656284, 18.8837809, 16.7699248, 16.4880773, 19.1656284, 18.1087003, 18.7428571, 18.8837809, 20.0816327, 19.377014, 21.0680988, 21.9136412, 21.9841031, 21.9841031, 19.5883996, 19.3065521, 22.4068743, 20.8567132, 21.8431794, 21.9841031, 22.4068743, 23.111493, 24.3093448, 24.9435016, 24.8730397, 24.732116, 22.9705693, 21.7727175, 23.7456498, 23.3933405, 23.4638024, 24.9435016, 24.3093448, 24.8730397, 24.9435016, 26.9868958, 26.2118153, 23.7456498, 24.3798067, 23.3933405, 25.2253491, 24.9435016, 26.352739, 23.2524168, 26.0004296, 25.6481203, 26.0708915, 28.6779807, 27.409667, 25.2253491, 25.5071966, 23.9570354, 26.352739, 25.6481203, 25.2253491, 25.0139635, 27.0573577, 25.5071966, 27.8324382, 29.6644468, 27.1278195, 26.7050483, 25.2253491, 24.4502685, 26.2822771, 28.6779807, 25.6481203, 28.3961332, 28.6075188, 27.6210526, 30.5804511, 32.5533835, 31.0736842, 30.650913, 27.9029001, 28.9598281, 32.8352309, 29.8758324, 31.9896885, 30.2986037, 30.7213749, 31.707841, 33.9626208, 34.1740064, 34.5263158, 34.1035446, 32.2010741, 31.0736842, 35.6537057, 32.1306122, 33.0466165, 33.892159, 32.1306122, 33.4693878, 34.6672395, 36.3583244, 35.6537057, 32.6943072, 33.4693878, 31.9896885, 35.3718582, 32.8352309, 35.8650913, 33.8216971, 34.1740064, 34.8081633, 35.8650913, 39.3881847, 37.7675618, 32.9761547, 33.6807734, 31.6373792, 35.7946294, 35.512782, 36.9924812, 32.8352309, 36.6401719, 34.8786251, 37.626638, 41.5725027, 38.6131042, 35.0900107, 36.4287863, 35.7946294, 37.4857143, 40.1632653, 35.4423201, 39.5995704, 39.8814178, 35.6537057, 41.5725027, 43.4749731, 41.8543502, 40.7974221, 37.4152524, 38.0494092, 43.0522019, 40.7269603, 41.1497315, 40.6564984, 39.9518797, 38.4721805, 44.0386681, 44.0386681, 43.4045113, 43.4045113, 38.8244898, 38.6835661, 45.5183673, 40.6564984, 41.924812, 42.5589689, 39.8814178, 39.3177229, 43.6863588, 43.4045113, 42.6998926, 40.9383459, 37.9789474, 36.4287863, 39.5995704, 39.0358754, 37.9789474, 41.5020408, 38.6835661, 37.6970999, 41.7838883, 44.7432868, 44.0386681, 42.6294307, 38.8949517, 39.4586466, 42.488507, 42.2066595, 44.5319012, 40.7269603, 43.3340494, 42.5589689, 46.6457573, 51.0143931, 48.5482277, 45.4479055, 45.3774436, 44.3909774, 48.8300752, 48.4777658, 48.1959184, 48.6891515, 49.3937701, 45.5183673, 52.9168636, 56.9331901, 52.9168636, 50.6620838, 47.843609, 48.8300752, 51.9303974, 55.8058002, 49.8870032, 54.3965628, 53.1987111, 47.2094522, 54.9602578, 57.0741139, 54.819334, 53.4805585, 48.8300752, 50.027927, 57.003652, 53.6919441, 51.9303974, 53.9737916, 52.2122449, 47.6322234, 55.7353383, 56.2285714, 55.4534909, 52.8464017, 49.5346939, 52.0008593, 57.2150376, 53.2691729, 54.6079484, 56.6513426, 51.1553169, 51.0143931, 55.5944146, 59.7516649, 59.4698174, 54.6784103, 55.1011815, 55.1716434, 60.5972073, 56.439957, 60.3153598, 57.1445757, 53.2691729, 54.9602578, 58.2719656, 64.6839957, 61.7950591, 54.3965628, 55.946724, 54.7488722, 58.9061224, 58.06058, 60.1744361, 53.6919441, 56.1581096, 50.6620838, 57.2854995, 61.7950591, 56.8627282, 50.6620838, 50.52116, 49.8870032, 51.4371643, 55.4534909, 50.0983888, 52.5645542, 49.6051557, 46.5048335, 55.8058002, 59.5402793, 55.4534909, 53.5510204, 51.084855, 53.6919441, 60.1744361, 56.7218045, 57.003652, 58.06058, 52.141783, 51.5076262, 60.8085929, 60.6676692, 61.0199785, 59.5402793, 53.9737916, 55.1011815, 63.979377, 56.5808808, 57.6378088, 59.3288937, 51.2962406, 53.9033298, 61.7950591, 61.1609023, 61.7245972, 60.0335124, 57.003652, 54.819334, 58.3424275, 57.9196563, 56.3694952, 61.3722879, 53.6919441, 54.8897959, 61.8655209, 63.9089151, 63.3452202, 59.9630505, 57.7082707, 56.7922664, 63.8384533, 59.9630505, 63.2042965, 61.6541353, 59.1175081, 57.003652, 61.6541353, 68.3480129, 66.7273899, 61.2313641, 61.3722879, 62.4292159, 65.036305, 62.7110634, 60.244898, 60.5267454, 60.0335124, 53.762406, 63.4156821, 66.8683136, 62.2178303, 60.0335124, 55.5944146, 58.8356606, 64.3316864, 60.8085929, 58.7651987, 58.8356606, 58.9061224, 52.7759398, 64.4021482, 65.8818475, 63.5566058, 61.0199785, 53.9737916, 57.003652, 66.5160043, 60.738131, 58.4833512, 59.6107411, 53.762406, 51.8599356, 61.2313641, 59.8221267, 59.8925886, 58.7651987, 53.1987111, 54.2556391, 63.8384533, 57.2150376, 58.06058, 61.0199785, 52.9168636, 53.3396348, 63.0633727, 62.0769066, 63.6270677, 62.0064447, 57.6378088, 60.738131, 63.2042965, 58.06058, 61.6541353, 52.9873255, 57.9901182, 55.6648765, 61.3722879, 68.9117078, 66.0932331, 59.5402793, 59.5402793, 59.5402793, 65.3886144, 62.7815252, 62.8519871, 60.8790548, 63.1338346, 56.2285714, 65.4590763, 72.0120301, 67.3615467, 61.1609023, 61.1609023, 61.5132116, 62.9929108, 69.4754028, 60.9495166, 65.5295381, 64.4021482, 56.0171858, 68.1366273, 72.2234157, 66.5160043, 63.5566058, 57.8491944, 62.1473684, 67.5729323, 65.4590763, 63.4156821, 63.6270677, 62.7110634, 57.003652, 68.6298604, 71.1664876, 69.4049409, 66.2341568, 58.8356606, 65.036305, 72.575725, 64.543072, 66.0227712, 68.7003222, 60.5972073, 63.4156821, 71.8711063, 71.6597207, 72.6461869, 69.898174, 65.6, 65.7409237, 72.2938776, 71.518797, 65.7409237, 75.9578947, 65.3181525, 65.5295381, 73.2803437, 77.5785177, 76.1692803, 69.7572503, 69.6867884, 69.2640172, 78.8468314, 69.8277121, 75.5351235, 72.9984962, 70.7437164, 69.6867884, 75.4646617, 82.7222342, 80.5379162, 71.0960258, 74.196348, 72.6461869, 77.437594, 79.6219119, 72.9280344, 80.0446831, 75.1828142, 67.220623, 78.3535983, 83.0040816, 79.2696026, 72.1529538, 72.8575725, 75.3237379, 81.4539205, 77.0852846, 73.13942, 77.2262084, 73.13942, 64.2612245, 79.5514501, 79.6923738, 76.0988185, 69.4049409, 60.5972073, 64.1907626, 76.380666, 68.2070892, 65.4590763, 71.8006445, 63.4156821, 62.0769066, 74.0554243, 72.2234157, 72.1529538, 69.4754028, 61.1609023, 64.8953813, 76.6625134, 67.4320086, 68.7707841, 74.6191192, 65.4590763, 64.4021482, 76.4511278, 73.0689581, 77.0852846, 73.2803437, 65.6, 67.3, 78.8, 66.5, 73.3, 73.1, 66.8, 65.4, 74.8, 72.8, 76.7, 67.3, 65.8, 67.6, 72.5, 69.5, 69.6, 70.2, 70.8, 64.2, 74.8, 76.2, 75.6, 66.6, 65.4, 67.1, 70.1, 76.9, 66.7, 77.4, 75.9, 63.3, 78.8, 79.7, 77.4, 71.7, 68, 71, 82.1, 75.6, 73.1, 77.9, 79.1, 66.9, 81.2, 81.6, 80.3, 73.3, 66.1, 70.1, 83.4, 75.3, 72.7, 80.9, 76.3, 71, 83.3, 81.2, 84.7, 78.3, 69.6, 78.1, 86.9, 74.8, 86.9, 79.6, 79, 77.9, 86.1, 85.6, 90, 79.1, 78.5, 79.1, 89.1, 78.4, 84.1, 82.1, 80.4, 77.8, 81.8, 85.8, 85.6, 71.3, 75.4, 75.3, 80.7, 82.9, 76.9, 82.9, 83.3, 75, 84.4, 86.2, 85.6, 73.8, 75.7, 77.3, 82.9, 80.4, 79.1, 78.8, 84.4, 70.3, 85.4, 87.4, 85.3, 77.7, 74.4, 78.1, 91.1, 83.1, 80.3, 88.4, 84.7, 77.1, 89.9, 87.9, 89.7, 82.1, 77.2, 79.2, 88.2, 88.3, 81.8, 94, 84.9, 80.9, 94.5, 89.3, 95.3, 86.5, 82.9, 84, 98.1, 83.9, 93.8, 93.3, 89.7, 88.3, 97.1, 96.3, 102.2, 89.2, 91, 91, 104.1, 92.3, 96.6, 99.8, 98.6, 93.5, 100.1, 106.8, 107.2, 89.3, 96.4, 99.3, 97.8, 107.9, 95.8, 104.8, 102.2, 89.1, 105.7, 103, 95, 82.4, 73.6, 74.5, 85.3, 76.6, 77, 82.7, 83.2, 73.1, 91.3, 89.5, 89.7, 79.6, 74.1, 79.5, 97.1, 87.7, 87, 95.9, 90.6, 85.3, 100, 98.2, 101.9, 93.4, 86.3, 93, 107.9, 94.1, 105.2, 96.3, 97.5, 95.5, 105.6, 98.8, 106.4, 94.7, 91.1, 96.1, 107.5, 93.1, 98, 99.8, 99.3, 94.4, 97.6, 102.4, 103.1, 85.7, 89.8, 90.9, 98.4, 100.2, 93.5, 99.6, 100.9, 91.6, 101.8, 104, 104, 90.8, 92.7, 96.2, 103.6, 99.1, 97.8, 97.5, 105.2, 87, 106.5, 105.7, 102.7, 94.1, 88.7, 94.5, 108.8, 99.6, 92.7, 106.3, 105.8, 89.3, 106.8, 106.4, 106.1, 94.9, 88.2, 100.2, 106.1, 104.5, 96.3, 110.6, 96.8, 98, 109.1, 100.3, 110.2, 96.8, 94.7, 97.7, 116.2, 95.9, 107.2, 106.3, 101.1, 102.8, 109.8, 102.9, 117.3, 100.3, 102.2};
    public static final double[] UZ4712z = {0.51358025, -0.2740741, 1.40987654, -1.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, -0.2740741, -1.5901235, 1.12345679, -0.4691358, 0.87160494, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, 0.72592593, -0.5901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, -0.2740741, 0.40987654, 0.12345679, -1.6691358, 1.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.2765432, 0, -1.4864198, 0.12592593, 1.00987654, 0.12345679, 0.3308642, 0.87160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.02345679, 0, -0.4864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, 0.02345679, 0, 0.51358025, 0.72592593, -0.5901235, 0.12345679, -0.4691358, 0.87160494, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, 0.12592593, -0.9901235, 0.12345679, 1.3308642, -2.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -0.9765432, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, -0.2740741, -1.5901235, 2.12345679, -2.4691358, 0.87160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.2765432, 0, -1.4864198, 0.92592593, 1.20987654, -0.8765432, 1.3308642, -0.9283951, -1.1481481, 0.79506173, 0.59259259, -0.7901235, 0.02345679, 0, 0.51358025, -0.2740741, 0.40987654, -0.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, 0.12592593, 0.00987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, -0.2740741, -0.5901235, 0.12345679, 1.3308642, -2.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -0.9765432, 0, 0.51358025, -0.2740741, -1.5901235, 2.12345679, -2.4691358, 0.87160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.2765432, 0, -1.4864198, 0.12592593, 1.00987654, 0.12345679, 0.3308642, -0.1283951, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.02345679, 0, -0.4864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, 0.02345679, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, 0.72592593, -0.5901235, 0.12345679, 1.3308642, -2.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -0.9765432, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, -0.2740741, -1.5901235, 2.12345679, -2.4691358, 0.87160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.2765432, 0, -1.4864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 0.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.02345679, 0, -0.4864198, 0.72592593, 0.40987654, -0.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, 0.12592593, 0.00987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, -0.2740741, -0.5901235, 0.12345679, 1.3308642, -2.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -0.9765432, 0, 0.51358025, -0.2740741, -2.5901235, 2.12345679, -1.4691358, -0.1283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, 0.12592593, 1.00987654, 0.12345679, 0.3308642, -0.1283951, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.02345679, 0, -0.4864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, 0.02345679, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, -0.2740741, 0.40987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, 0.72592593, -0.5901235, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, 0.12592593, 0.00987654, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.3765432, 0, -1.4864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 0.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, -0.0765432, 0, -0.4864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, -0.0765432, 0, 0.51358025, 1.12592593, 0.00987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 1.20987654, -0.0765432, 0, 0.81358025, -0.2740741, -0.5901235, 0.12345679, 0.3308642, -1.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -1.0765432, 0, 0.51358025, -0.2740741, -2.5901235, 2.12345679, -1.4691358, -0.1283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.3765432, 0, -0.4864198, 0.12592593, 0.00987654, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.3765432, 0, -1.4864198, 0.72592593, 1.40987654, -0.8765432, 0.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, -0.0765432, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, -0.0765432, 0, 0.81358025, -0.2740741, 0.40987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 0.20987654, -0.1765432, 0, 0.81358025, -0.2740741, -1.5901235, 1.12345679, -0.4691358, -0.1283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -1.1765432, 0, 0.41358025, 0.12592593, 0.00987654, 0.12345679, -0.6691358, 0.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.3765432, 0, -1.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, -0.1765432, 0, -0.5864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, 0.59259259, -1.7901235, -0.1765432, 0, 0.41358025, -0.2740741, 1.40987654, -1.8765432, 1.3308642, 0.07160494, -1.1481481, 0.79506173, -0.4074074, -0.7901235, 0.82345679, 0, 0.81358025, 0.72592593, -0.5901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0, 0.41358025, -0.2740741, -2.5901235, 2.12345679, -1.4691358, 0.87160494, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 0.40987654, 0.12345679, -0.6691358, 0.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, 0.62345679, 0, -1.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.82345679, 0, -0.5864198, 1.12592593, 1.00987654, -1.8765432, 2.3308642, -0.9283951, -1.1481481, 0.79506173, -0.4074074, -0.7901235, 0.82345679, 0, 0.81358025, -0.2740741, 0.40987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 0.20987654, 0.82345679, 0, 0.81358025, -0.2740741, -1.5901235, 1.12345679, -0.4691358, -0.1283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0, 0.41358025, 0.12592593, -0.9901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.82345679, 0, -0.5864198, -0.2740741, -0.5901235, 1.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, 0.59259259, -1.7901235, 0.82345679, 0, 0.41358025, -0.2740741, 1.40987654, -1.8765432, 1.3308642, 0.07160494, -1.1481481, 0.79506173, -0.4074074, -0.7901235, 0.82345679, 0, 0.81358025, -0.2740741, 0.40987654, -0.8765432, 0.3308642, 0.07160494, -0.1481481, 0.79506173, -1.4074074, 0.20987654, 0.82345679, 0, 0.81358025, 0.72592593, -2.5901235, 2.12345679, -0.4691358, 0.87160494, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 0.40987654, 0.12345679, -0.6691358, 0.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, 0.62345679, 0, -1.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.82345679, 0, -0.5864198, 0.12592593, 1.00987654, -0.8765432, 2.3308642, -0.9283951, -1.1481481, 0.79506173, 0.59259259, -1.7901235, 0.82345679, 0, 0.41358025, 0.72592593, 0.40987654, -0.8765432, 0.3308642, 0.07160494, -0.1481481, 0.79506173, -1.4074074, 0.20987654, 0.82345679, 0, 0.81358025, -0.2740741, -1.5901235, 1.12345679, -0.4691358, -0.1283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0, 0.41358025, 0.12592593, -0.9901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 0.40987654, 0.12345679, -1.6691358, 1.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, 0.62345679, 0, -1.5864198, 0.72592593, -0.5901235, 1.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, 0.59259259, -1.7901235, 0.82345679, 0, 0.41358025, -0.2740741, 1.40987654, -1.8765432, 1.3308642, 0.07160494, -1.1481481, 0.79506173, -0.4074074, -1.7901235, 0.82345679, 0, 0.81358025, -0.2740741, -0.5901235, 0.12345679, -0.4691358, 0.87160494, -0.1481481, 0.79506173, -1.4074074, 0.20987654, 0.82345679, 0, 0.81358025, 0.12592593, -0.9901235, 0.12345679, 1.3308642, -1.9283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0, 0.41358025, -0.2740741, 0.40987654, 0.12345679, -0.6691358, 0.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, 0.62345679, 0, -1.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.82345679, 0, -0.5864198, -0.0740741, 1.20987654, -0.8765432, 1.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -1.7901235, 0.82345679, 0, 0.41358025, -0.2740741, 1.40987654, -1.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, -0.4074074, -0.7901235, 0.82345679, 0, 0.81358025, 0.72592593, -1.5901235, 1.12345679, -0.4691358, -0.1283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0, 0.41358025, 0.12592593, -0.9901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 0.40987654, 0.12345679, -1.6691358, 1.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, 0.62345679, 0, -1.5864198, -0.2740741, -0.5901235, 2.12345679, -1.4691358, 1.87160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.82345679, 0, -0.5864198, 0.72592593, 1.40987654, -1.8765432, 1.3308642, 0.07160494, -1.1481481, 0.79506173, -0.4074074, -0.7901235, 0.82345679, 0, 0.81358025, -0.2740741, -0.5901235, 0.12345679, -0.4691358, 0.87160494, -0.1481481, 0.79506173, -1.4074074, 0.20987654, 0.82345679, 0, 0.81358025, 0.12592593, -0.9901235, 0.12345679, 1.3308642, -1.9283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0};

    public static final double[] UZ4712z_k = {0.51358025, -0.2740741, 1.40987654, -1.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, -0.2740741, -1.5901235, 1.12345679, -0.4691358, 0.87160494, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, 0.72592593, -0.5901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, -0.2740741, 0.40987654, 0.12345679, -1.6691358, 1.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.2765432, 0, -1.4864198, 0.12592593, 1.00987654, 0.12345679, 0.3308642, 0.87160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.02345679, 0, -0.4864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, 0.02345679, 0, 0.51358025, 0.72592593, -0.5901235, 0.12345679, -0.4691358, 0.87160494, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, 0.12592593, -0.9901235, 0.12345679, 1.3308642, -2.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -0.9765432, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, -0.2740741, -1.5901235, 2.12345679, -2.4691358, 0.87160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.2765432, 0, -1.4864198, 0.92592593, 1.20987654, -0.8765432, 1.3308642, -0.9283951, -1.1481481, 0.79506173, 0.59259259, -0.7901235, 0.02345679, 0, 0.51358025, -0.2740741, 0.40987654, -0.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, 0.12592593, 0.00987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, -0.2740741, -0.5901235, 0.12345679, 1.3308642, -2.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -0.9765432, 0, 0.51358025, -0.2740741, -1.5901235, 2.12345679, -2.4691358, 0.87160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.2765432, 0, -1.4864198, 0.12592593, 1.00987654, 0.12345679, 0.3308642, -0.1283951, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.02345679, 0, -0.4864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, 0.02345679, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, 0.72592593, -0.5901235, 0.12345679, 1.3308642, -2.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -0.9765432, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, -0.2740741, -1.5901235, 2.12345679, -2.4691358, 0.87160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.2765432, 0, -1.4864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 0.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.02345679, 0, -0.4864198, 0.72592593, 0.40987654, -0.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, 0.12592593, 0.00987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, -0.2740741, -0.5901235, 0.12345679, 1.3308642, -2.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -0.9765432, 0, 0.51358025, -0.2740741, -2.5901235, 2.12345679, -1.4691358, -0.1283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, 0.12592593, 1.00987654, 0.12345679, 0.3308642, -0.1283951, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.02345679, 0, -0.4864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, 0.02345679, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, 0.02345679, 0, 0.81358025, -0.2740741, 0.40987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 1.20987654, 0.02345679, 0, 0.81358025, 0.72592593, -0.5901235, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.2765432, 0, -0.4864198, 0.12592593, 0.00987654, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.3765432, 0, -1.4864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 0.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, -0.0765432, 0, -0.4864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, -0.0765432, 0, 0.51358025, 1.12592593, 0.00987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 1.20987654, -0.0765432, 0, 0.81358025, -0.2740741, -0.5901235, 0.12345679, 0.3308642, -1.9283951, 0.85185185, -0.2049383, -0.4074074, 1.20987654, -1.0765432, 0, 0.51358025, -0.2740741, -2.5901235, 2.12345679, -1.4691358, -0.1283951, 0.85185185, -1.2049383, 0.59259259, 1.20987654, -1.3765432, 0, -0.4864198, 0.12592593, 0.00987654, 0.12345679, -0.6691358, -0.9283951, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.3765432, 0, -1.4864198, 0.72592593, 1.40987654, -0.8765432, 0.3308642, 0.07160494, -1.1481481, 0.79506173, 0.59259259, -0.7901235, -0.0765432, 0, 0.51358025, -0.2740741, -0.5901235, 0.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, -0.4074074, 0.20987654, -0.0765432, 0, 0.81358025, -0.2740741, 0.40987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 0.20987654, -0.1765432, 0, 0.81358025, -0.2740741, -1.5901235, 1.12345679, -0.4691358, -0.1283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -1.1765432, 0, 0.41358025, 0.12592593, 0.00987654, 0.12345679, -0.6691358, 0.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, -0.3765432, 0, -1.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, -0.1765432, 0, -0.5864198, -0.2740741, 1.40987654, -0.8765432, 0.3308642, 1.07160494, -1.1481481, 0.79506173, 0.59259259, -1.7901235, -0.1765432, 0, 0.41358025, -0.2740741, 1.40987654, -1.8765432, 1.3308642, 0.07160494, -1.1481481, 0.79506173, -0.4074074, -0.7901235, 0.82345679, 0, 0.81358025, 0.72592593, -0.5901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0, 0.41358025, -0.2740741, -2.5901235, 2.12345679, -1.4691358, 0.87160494, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 0.40987654, 0.12345679, -0.6691358, 0.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, 0.62345679, 0, -1.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.82345679, 0, -0.5864198, 1.12592593, 1.00987654, -1.8765432, 2.3308642, -0.9283951, -1.1481481, 0.79506173, -0.4074074, -0.7901235, 0.82345679, 0, 0.81358025, -0.2740741, 0.40987654, -0.8765432, 1.3308642, -0.9283951, -0.1481481, 0.79506173, -1.4074074, 0.20987654, 0.82345679, 0, 0.81358025, -0.2740741, -1.5901235, 1.12345679, -0.4691358, -0.1283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0, 0.41358025, 0.12592593, -0.9901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.82345679, 0, -0.5864198, -0.2740741, -0.5901235, 1.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, 0.59259259, -1.7901235, 0.82345679, 0, 0.41358025, -0.2740741, 1.40987654, -1.8765432, 1.3308642, 0.07160494, -1.1481481, 0.79506173, -0.4074074, -0.7901235, 0.82345679, 0, 0.81358025, -0.2740741, 0.40987654, -0.8765432, 0.3308642, 0.07160494, -0.1481481, 0.79506173, -1.4074074, 0.20987654, 0.82345679, 0, 0.81358025, 0.72592593, -2.5901235, 2.12345679, -0.4691358, 0.87160494, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 0.40987654, 0.12345679, -0.6691358, 0.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, 0.62345679, 0, -1.5864198, -0.2740741, 1.40987654, 0.12345679, -0.6691358, 1.07160494, -0.1481481, -0.0049383, 0.59259259, -0.7901235, 0.82345679, 0, -0.5864198, 0.12592593, 1.00987654, -0.8765432, 2.3308642, -0.9283951, -1.1481481, 0.79506173, 0.59259259, -1.7901235, 0.82345679, 0, 0.41358025, 0.72592593, 0.40987654, -0.8765432, 0.3308642, 0.07160494, -0.1481481, 0.79506173, -1.4074074, 0.20987654, 0.82345679, 0, 0.81358025, -0.2740741, -1.5901235, 1.12345679, -0.4691358, -0.1283951, 0.85185185, -0.2049383, -0.4074074, 0.20987654, -0.1765432, 0, 0.41358025, 0.12592593, -0.9901235, 0.12345679, 0.3308642, -0.9283951, 0.85185185, -1.2049383, 0.59259259, 0.20987654, -0.3765432, 0, -0.5864198, -0.2740741, 0.40987654, 0.12345679, -1.6691358, 1.07160494, 0.85185185, -1.0049383, 0.59259259, 0.20987654, 0.62345679, 0, -1.5864198, 0.72592593, -0.5901235, 1.12345679, -0.4691358, 1.87160494, -1.1481481, 0.79506173, 0.59259259, -1.7901235, 0.82345679, 0, 0.41358025, -0.2740741, 1.40987654, -1.8765432, 1.3308642, 0.07160494, -1.1481481, 0.79506173, -0.4074074, -1.7901235, 0.82345679, 0, 0.81358025};
    public static final double[] UY8413z = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1.75308642, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -0.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2.2469136, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1.2469136};

    public static final TsData ts_Prod_B_C = TsData.ofInternal(TsPeriod.monthly(1967, 1), Prod_B_C);
    public static final Matrix x = Matrix.builder(UZ4712z_k)
            .nrows(UZ4712z_k.length)
            .ncolumns(1)
            .build();// nur ein Regressor drin
//zweiter Regressor fehlt
    //wie kürzt man die Länge von einem Regres

    @Test
    public void test1() {
        CompositeModel model = new CompositeModel();
        model.add(AtomicModels.localLinearTrend("l", .01, .01, false, false));
        model.add(AtomicModels.seasonalComponent("s", "Crude", 12, .01, false));
        model.add(AtomicModels.noise("n", .01, false));
        //model.add(AtomicModels.regression("r", x));
         model.add(AtomicModels.timeVaryingRegression("r", x, .01, false));
        ModelEquation eq = new ModelEquation("eq1", 0, true);
        eq.add("l");
        eq.add("s");
        eq.add("n");
        eq.add("r");
        model.add(eq);
        int len = Prod_B_C.length;
        Matrix M = Matrix.make(len, 1);
        M.column(0).copyFrom(Prod_B_C, 0);
        CompositeModelEstimation rslt = model.estimate(M, 1e-15, false, true, null);
        // CompositeModelEstimation rslt = model.estimate(M, 1e-15, false, true, null);
//        System.out.println(DataBlock.ofInternal(rslt.getFullParameters()));
//        System.out.println(rslt.getSmoothedStates().getComponent(0));
//        System.out.println(rslt.getSmoothedStates().getComponentVariance(0));
//        System.out.println(rslt.getLikelihood().logLikelihood());
    }}
