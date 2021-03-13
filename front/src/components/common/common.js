import moment from 'moment'
import {msg} from './msg.js'
import {validate} from './validate.js'
import {api} from './api.js'
export const common = {
	mixins: [msg, validate, api],
	data () {
		return {
			onProgress : false,

			userId: 'piazza100',
			password: '123qwe!@#',
			content:'',
			title:'',

			userNo:'',
			adminUserNo:'',
			approvalVo:'',
			approvalVoList:'',
		}
	},
	filters: {
		formatDate: function (value) {
			if (value) {
				return moment(value).format('YYYY/MM/DD HH:mm:ss')
			}
		},
	},
}