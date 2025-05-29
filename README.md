# Java 实验习题管理系统

## 项目简介
本系统是面向高校的Java实验教学管理平台，支持教师端实验发布与作业批改、学生端实验查看与作业提交等核心功能，包含前后端完整实现。

## 核心功能
- **角色权限**：区分教师/学生角色，支持登录注册（用户名/密码验证）
- **实验管理**：教师可创建/编辑实验（含标题、描述、截止时间），学生可查看实验列表
- **作业提交**：学生支持代码文本粘贴、ZIP文件上传、运行结果截图上传
- **作业批改**：教师可查看学生提交记录，包含代码下载、结果图查看等操作
- **文件存储**：代码与结果图自动分类存储至`uploads/code/`和`uploads/result/`目录

## 技术栈详情
### 后端（Java）
- 框架：Spring Boot 3.2.4（基于Java 17）
- ORM：MyBatis-Plus 3.5.4（简化数据库操作）
- 数据库：MySQL 8.0.35（通过`create_tables.sql`初始化表结构）
- 构建工具：Maven 3.9.6（依赖管理）
- 其他：Jackson 2.15.2（JSON序列化）、HikariCP 5.0.1（数据库连接池）

### 前端（Vue 3）
- 框架：Vue 3.3.13 + Vue Router 4.2.5（SPA路由管理）
- UI组件：Element Plus 2.4.7（桌面端组件库）、Bootstrap 5.3.2（基础布局样式）
- 网络请求：Axios 1.5.0（封装API调用）
- 构建工具：Vite 4.4.9（快速构建）

## 环境准备
1. **JDK**：JDK 17+（推荐OpenJDK 17.0.9）
2. **Node.js**：v18.18.2（前端依赖安装）
3. **MySQL**：8.0+（创建数据库`javahw_db`，字符集`utf8mb4`）
4. **IDE**：IntelliJ IDEA（后端）或VS Code（前端）

## 本地运行指南
### 步骤1：初始化数据库
1. 登录MySQL客户端
2. 执行`create_tables.sql`脚本：
   ```sql
   source /path/to/JavaHW/create_tables.sql
   ```

### 步骤2：配置后端
1. 打开`src/main/resources/application.yml`
2. 修改数据库连接配置：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/javahw_db?useSSL=false&serverTimezone=Asia/Shanghai
       username: root
       password: your_mysql_password
   ```

### 步骤3：启动后端
1. 在IntelliJ中打开`JavaHwApplication.java`
2. 右键点击`main`方法选择`Run 'JavaHwApplication'`
   - 成功启动日志：`Tomcat started on port(s): 8080 (http)`

### 步骤4：启动前端
1. 打开终端进入前端目录：
   ```bash
   cd /path/to/JavaHW/javahw-frontend
   ```
2. 安装依赖：
   ```bash
   npm install
   ```
3. 启动开发服务器：
   ```bash
   npm run dev
   ```
   - 访问地址：`http://localhost:5173`（自动打开浏览器）

## 项目结构
```
JavaHW/
├── src/                # 后端核心代码
│   ├── java/          # Spring Boot主代码（控制器、服务层、实体类）
│   └── resources/     # 配置文件（application.yml）、MyBatis映射文件
├── javahw-frontend/   # 前端项目
│   ├── src/           # Vue核心代码（视图、组件、API封装）
│   ├── public/        # 静态资源（背景图、图标）
│   └── vite.config.js # Vite构建配置
├── uploads/           # 文件存储目录（自动创建）
│   ├── code/         # 学生提交的代码文件
│   └── result/       # 学生提交的运行结果图
├── pom.xml            # 后端Maven依赖配置
└── create_tables.sql  # 数据库表结构初始化脚本
```

## 注意事项
- 前端API请求默认指向`http://localhost:8080`，如需修改后端地址，需调整`javahw-frontend/src/api/axios.js`中的`baseURL`
- 文件上传大小限制在后端`application.yml`中配置（默认100MB）
- 生产环境部署需通过`npm run build`打包前端，将`dist`目录部署至Nginx等静态服务器

## 贡献说明
欢迎提交Issue反馈BUG或建议，PR请遵循以下规范：
1. 提交前同步最新主分支代码
2. 新增功能需提供单元测试（后端）或E2E测试（前端）
3. 代码风格符合项目现有规范（使用Prettier格式化前端代码）

## 许可协议
本项目采用MIT许可协议，详见`LICENSE`文件（需自行创建）
